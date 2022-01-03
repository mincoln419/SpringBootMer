import axios from 'axios';
import {all, fork, take, takeEvery, takeLatest, call, put} from 'redux-saga/effects';
import { GET_TOKEN, LOG_IN_FAILURE, LOG_IN_REQUEST, LOG_IN_SUCCESS, LOG_OUT_FAILURE, LOG_OUT_REQUEST, LOG_OUT_SUCCESS } from '../actions';
import Router from 'next/router'


/* API CALL */
function loginAPI(data){
    console.log("data", data);
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
            'username': data.loginId,
            'password': data.password, //비밀번호 input암호화 필요 -> 백엔드 서버에서 다시한번 암호화 해서 DB에 저장
            'grant_type': 'password'
        }
      });
};

function getAccountAPI(data, token){
    
    console.log(token);
    return axios.request({
        url: "/api/account/login/"+ data.loginId,
        method: "get",
        baseURL: "/",
        headers: {'Content-Type' : 'application/json;charset=UTF-8',
                  'Accept':'application/hal+json',
                  'Authorization' : 'Bearer ' + token
              }
      });
}


/* login Action */
function* login(data){
    try{
        const result = yield call(loginAPI, data);//call - 동기, fork - 비동기
        yield put({
            type: GET_TOKEN,
            token: result.data.access_token
        });

        const account = yield call(getAccountAPI, data, result.data.access_token); 

        yield put({
            type: LOG_IN_SUCCESS,
            data: account.data
        });

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

export default function* userSaga() {
    yield all([
        fork(watchLogIn),
        fork(watchLogOut),
    ]);
};