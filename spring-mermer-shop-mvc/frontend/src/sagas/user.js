import axios from 'axios';
import {all, fork, take, takeEvery, takeLatest, call, put} from 'redux-saga/effects';
import { GET_TOKEN, LOG_IN_FAILURE, LOG_IN_REQUEST, LOG_IN_SUCCESS, LOG_OUT_FAILURE, LOG_OUT_REQUEST, LOG_OUT_SUCCESS, SIGN_UP_FAILURE, SIGN_UP_REQUEST, SIGN_UP_SUCCESS, SING_UP_REQUEST } from '../actions';
import Router from 'next/router'


/* API CALL */
function loginAPI(data){
    
    return  axios.request({
        'url': "/oauth/token",
        'method': "post",
        'baseURL': "/",
        'headers': {'Content-Type' : 'form-data'},
        'auth': {//Basic Auth를 만들어주는 해더부분임
          'username': 'merApp0203041910112',
          'password': 'mermer110129345671'
        },
        'params' : {
            'username': data.login,
            'password': data.pass, //비밀번호 input암호화 필요 -> 백엔드 서버에서 다시한번 암호화 해서 DB에 저장
            'grant_type': 'password'
        }
      });
};

function getAccountAPI(data, token){

    return axios.request({
        url: "/api/account/login/"+ data.login,
        method: "get",
        baseURL: "/",
        headers: {'Content-Type' : 'application/json;charset=UTF-8',
                  'Accept':'application/hal+json',
                  'Authorization' : 'Bearer ' + token
              }
      });
}


/* login Action */
function* login(action){
    try{
        const result = yield call(loginAPI, action.Account);//call - 동기, fork - 비동기
        yield put({
            type: GET_TOKEN,
            Account: { token: result.data.access_token }
        });

        const account = yield call(getAccountAPI, action.Account, result.data.access_token); 

        yield put({
            type: LOG_IN_SUCCESS,
            accountId: account.data.id,
            Account:{login : action.Account.login}
        });
        action.setCookie('token', result.data.access_token, {path: '/'});
        action.setCookie('accountId', account.data.id, {path: '/'});
        action.setCookie('loginId', action.Account.login, {path: '/'});
        yield Router.push("/");
    }catch(err){
        console.log(err);
        yield put({
            type: LOG_IN_FAILURE
        });
    }
    
};

function* watchLogIn() {
    yield takeLatest(LOG_IN_REQUEST, login);
};

/* log out Action */
function* logOut(){
    try{       
        yield put({
            type: LOG_OUT_SUCCESS,
        });
    }catch(err){
        yield put({
            type: LOG_OUT_FAILURE,
        });
    }
    
};

function* watchLogOut() {

    yield takeLatest(LOG_OUT_REQUEST, logOut);
};


function signUpAPI(data){
    return axios.request({
        url: "/api/account",
        method: "post",
        baseURL: "/",
        headers: {'Content-Type' : 'application/json;charset=UTF-8',
                  'Accept':'application/hal+json'
              },
        data: data
      });
}

/* log out Action */
function* signUp(action){
    try{
        yield call(signUpAPI, action.Account);//call - 동기, fork - 비동기
        
        yield put({
            type: SIGN_UP_SUCCESS,
        });

        yield put({
            type: LOG_IN_REQUEST,
            Account: {
                login: action.Account.login,
                pass: action.Account.pass,
            },
            setCookie: action.setCookie,
        });

    }catch(err){
        console.log(err);
        yield put({
            type: SIGN_UP_FAILURE,
        });
    }
    
};

function* watchSignUp() {

    yield takeLatest(SIGN_UP_REQUEST, signUp);
};

export default function* userSaga() {
    yield all([
        fork(watchLogIn),
        fork(watchLogOut),
        fork(watchSignUp),
    ]);
};