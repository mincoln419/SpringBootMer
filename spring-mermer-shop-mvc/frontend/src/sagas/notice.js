import {all, fork, take, takeEvery, takeLatest, call, put} from 'redux-saga/effects';


function noticeAPI(data){


    return  await axios.request({
        'url': "/notice",
        'method': "post",        
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

function* queryNotice(action){
    try{
        const result = yield call(noticeAPI, action.data);//call - 동기, fork - 비동기
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

function* watchQueryNotice() {
    yield throttle('QUERY_NOTICE', login, 2000);
};


function* watchAddNotice() {
    yield throttle('ADD_NOTICE', 1000);
};

export default function* userSaga() {
    yield all([
        fork(watchQueryNotice),
        fork(watchAddNotice)
    ]);
};