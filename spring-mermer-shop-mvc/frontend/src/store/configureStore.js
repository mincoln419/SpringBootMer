import { createStore } from 'redux';
import { createWrapper } from 'next-redux-wrapper';
import reducer from '../reducer';

//configureStore.js - redux 적용
const configureStore = () => {

    const store = createStore(reducer);
    return store;
}

const wrapper = createWrapper(configureStore, {
        debug: true
    });

export default wrapper;