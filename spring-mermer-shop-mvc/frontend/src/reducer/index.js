/* 초기 데이터 구조를 잡아놔야 한다 */
import { HYDRATE } from "next-redux-wrapper";

import user from "./user";
import notice from "./notice";
import comment from "./comment";
import menu from "./menu";
import { combineReducers } from "redux";

/* 비동기 action creator */
// (이전상태, 액션) => 다음상태
const rootReducer = (state, action) => {

    switch(action.type) {
        case HYDRATE : //서버사이드 랜더링 때문에 추가
            return { ...state,
                 ...action.payload};
        default: {
            const combinedReducer = combineReducers({
                user,
                notice,
                comment,
                menu
            });
            return combinedReducer(state, action);
        }
    }
};


export default rootReducer;

