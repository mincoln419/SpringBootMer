import {all, fork, take, takeEvery, takeLatest, call, put} from 'redux-saga/effects';



function loginAPI(data){
    return  await axios.request({
        'url': "/oauth/token",
        'method': "post",
        'baseURL': "/",
        'headers': {'Content-Type' : 'form-data'},
        'auth': {//Basic Auth를 만들어주는 해더부분임
          'username': 'merApp0203041910112',
          'password': 'mermer110129345671'
        },
        'params' : {
            'username': data.loginId,
            'password': data.password, //비밀번호 input암호화 필요 -> 백엔드 서버에서 다시한번 암호화 해서 DB에 저장
            'grant_type': 'password'
        }
      }).then(function(res) {
        const token = res.data.access_token;
        dispatch(getToken({token}));
        /* 로그인 아이디로 account_id를 가져와 reducer에 담음*/
        axios.request({
          url: "/api/account/login/"+ data.loginId,
          method: "get",
          baseURL: "/",
          headers: {'Content-Type' : 'application/json;charset=UTF-8',
                    'Accept':'application/hal+json',
                    'Authorization' : 'Bearer ' + token
                }
        }).then((resCall) => {
          const accountId = resCall.data.id;
          dispatch(loginAction({accountId}));
         // router.push("/");
        });
      });
};

function* login(action){
    try{
        const result = yield call(loginAPI, action.data);//call - 동기, fork - 비동기
        yield put({
            type: 'LOG_IN_SUCCESS',
            data: result.data
        });
    }catch(err){
        yield put({
            type: 'LOG_IN_FAILURE',
            data: result.data
        });
    }
    
};

function* watchLogIn() {
    yield takeLatest('LOG_IN_REQUEST', login);
};

function* logOut(action){
    try{
        const result = yield call(loginAPI, action.data);//call - 동기, fork - 비동기
        yield put({
            type: 'LOG_OUT_SUCCESS',
            data: result.data
        });
    }catch(err){
        yield put({
            type: 'LOG_OUT_FAILURE',
            data: result.data
        });
    }
    
};


function* watchLogOut() {
    yield takeLatest('LOG_OUT_REQUEST', logOut);
};

export default function* userSaga() {
    yield all([
        fork(watchLogIn),
        fork(watchLogOut),
    ]);
};