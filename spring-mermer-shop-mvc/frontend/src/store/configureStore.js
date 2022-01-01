import { applyMiddleware, compose, createStore} from 'redux';
import { createWrapper } from 'next-redux-wrapper';
import reducer from '../reducer';
import { composeWithDevTools } from 'redux-devtools-extension';

//configureStore.js - redux 적용
const configureStore = () => {

    const middlewares = [];
    //미들웨어 주입
    const enhancer = process.env.NODE_ENV === 'production'
    ? compose(applyMiddleware(...middlewares))
    : composeWithDevTools(applyMiddleware(...middlewares))
    ;

    const store = createStore(reducer, enhancer);
    return store;
}

const wrapper = createWrapper(configureStore, {
        debug: true
    });

export default wrapper;