import axios from 'axios';
import {all, fork, take, takeEvery, takeLatest, call, put} from 'redux-saga/effects';

import noticeSaga from './notice';
import userSaga from './user';


export default function* RootSaga(){

    yield all([
        fork(noticeSaga),
        fork(userSaga),
    ]);
}

