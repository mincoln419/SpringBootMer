import {all, fork, throttle, take, takeEvery, takeLatest, call, put} from 'redux-saga/effects';
import {QUERY_NOTICE_REQUEST, QUERY_NOTICE_SUCCESS, QUERY_NOTICE_FAILURE, UPLOAD_IMAGE_SUCCESS, UPLOAD_IMAGE_REQUEST, UPLOAD_IMAGE_FAILURE } from '../actions/notice';
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



function uploadImgAPI(data, token){
    for (let value of data.values()) {
        console.log("value", value);
      }
        return axios.request({
        url: "/api/common/img/",
        method: "post",
        baseURL: "/",
        headers: { 'Accept':'application/hal+json',
                  'Content-Type': 'multipart/form-data;utf-8',
                  'Authorization' : 'Bearer ' + token
              },
        data: data
      });
}


function* upLoadImages(action){

    try{
        const result = yield call(uploadImgAPI, action.tmpImgs, action.token);//call - 동기, fork - 비동기

        yield put({
            type: UPLOAD_IMAGE_SUCCESS,
            data: result.data
        });
    }catch(err){
        console.log(err);
        yield put({
            type: UPLOAD_IMAGE_FAILURE,
        });
    }
}


/* watch Action*/
function* watchQueryNotice() {
    yield throttle(2000, QUERY_NOTICE_REQUEST, queryNoticeRequest);
};


function* watchAddNotice() {
    yield throttle(1000, 'ADD_NOTICE_REQUEST', addNoticeRequest);
};


function* watchUploadImg() {
    yield throttle(2000, UPLOAD_IMAGE_REQUEST, upLoadImages);
};



export default function* noticeSaga() {
    yield all([
        fork(watchQueryNotice),
        fork(watchAddNotice),
        fork(watchUploadImg)
    ]);
};