import {all, fork, throttle, take, takeEvery, takeLatest, call, put} from 'redux-saga/effects';
import {QUERY_NOTICE_REQUEST, QUERY_NOTICE_SUCCESS, QUERY_NOTICE_FAILURE } from '../actions/notice';
import axios from 'axios';

function noticeQueryAPI(){
    return  axios.get("/api/notice");
};

function* queryNoticeRequest(){
    try{
        const result = yield call(noticeQueryAPI);//call - 동기, fork - 비동기
        console.log(result);
        yield put({
            type: QUERY_NOTICE_SUCCESS,
            data: result.data._embedded.tupleBackedMapList
        });
    }catch(err){
        console.log(err);
        yield put({
            type: QUERY_NOTICE_FAILURE,
        });
    }
    
};

function addNoticeAPI(data){
    return  axios.post("/api/notice", data);
}

function* addNoticeRequest(action){
    try{
        const result = yield call(addNoticeAPI, action.data);//call - 동기, fork - 비동기

        yield put({
            type: ADD_NOTICE_SUCCESS,
            data: result.data
        });
    }catch(err){
        yield put({
            type: ADD_NOTICE_FAILURE,
        });
    }
}


function* watchQueryNotice() {
    yield throttle(2000, 'QUERY_NOTICE_REQUEST', queryNoticeRequest);
};


function* watchAddNotice() {
    yield throttle(1000, 'ADD_NOTICE_REQUEST', addNoticeRequest);
};

export default function* noticeSaga() {
    yield all([
        fork(watchQueryNotice),
        fork(watchAddNotice)
    ]);
};