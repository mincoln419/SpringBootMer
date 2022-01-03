import { applyMiddleware, compose, createStore} from 'redux';
import { createWrapper } from 'next-redux-wrapper';
import reducer from '../reducer';
import { composeWithDevTools } from 'redux-devtools-extension';
import createSagaMiddleware from "react-saga";
import rootSaga from "../sagas";

//configureStore.js - redux 적용
const configureStore = () => {
    const sagaMiddleware = createSagaMiddleware();
    const middlewares = [sagaMiddleware];
    //미들웨어 주입
    const enhancer = process.env.NODE_ENV === 'production'
    ? compose(applyMiddleware(...middlewares))
    : composeWithDevTools(applyMiddleware(...middlewares))
    ;

    const store = createStore(reducer, enhancer);
    store.sagaTask = sagaMiddleware.run(rootSaga);
    return store;
}

const wrapper = createWrapper(configureStore, {
        debug: true
    });

export default wrapper;