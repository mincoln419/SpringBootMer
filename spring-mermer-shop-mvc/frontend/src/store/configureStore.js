import { applyMiddleware, compose, createStore} from 'redux';
import { createWrapper } from 'next-redux-wrapper';
import reducer from '../reducer';
import { composeWithDevTools } from 'redux-devtools-extension';
import createSagaMiddleware, { END } from "redux-saga";
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
    

    store.runSaga = () => {
        // Avoid running twice
        if (store.saga) return;
        store.saga = sagaMiddleware.run(rootSaga);
      };
    
    store.stopSaga = async () => {
        // Avoid running twice
        if (!store.saga) return;
        store.dispatch(END);
        await store.saga.toPromise();
        store.saga = null;
      };
    
    store.execSagaTasks = async (isServer, tasks) => {
        // run saga
        store.runSaga();
        // dispatch saga tasks
        tasks(store.dispatch);
        // Stop running and wait for the tasks to be done
        await store.stopSaga();
        // Re-run on client side
        if (!isServer) {
          store.runSaga();
        }
    };

    store.sagaTask = sagaMiddleware.run(rootSaga);
    return store;
}

const wrapper = createWrapper(configureStore, {
        debug: process.env.NODE_ENV === 'development'
    });

export default wrapper;