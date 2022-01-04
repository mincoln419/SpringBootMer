import produce from "immer";
import { GET_TOKEN, LOG_IN_FAILURE, LOG_IN_REQUEST, LOG_IN_SUCCESS, LOG_OUT_FAILURE, LOG_OUT_REQUEST, LOG_OUT_SUCCESS, SIGN_UP_FAILURE, SIGN_UP_REQUEST, SIGN_UP_SUCCESS } from "../actions";

const intitialState = {
    isLoggedIn : false,
    accountId: null,
    signUpData :{},
    loginData : {},
    isLogginIn : false,
    isLogginOut : false,
    Account:{
        login: null,
        pass: null,
        username: null,
        email: null,
        hpNum: null,
        role: [],
        part: [],
        token : null,
    }
};
/* Action Creator */
export const loginRequestAction = (data) => {
    return {
        type: LOG_IN_REQUEST,
        Account: {
            login: data.loginId,
            pass: data.password,
        }
    }
};

export const logoutAction = () => {
    return {
        type: LOG_OUT_REQUEST,
    }
};

export const signUpRequestAction = (data) =>{
    console.log(data);
    return {
        type: SIGN_UP_REQUEST,
        Account: data.user
    }
}


const reducer = (state = intitialState, action) => {
    
    return produce(state, (draft) => {
    switch (action.type) {
        case LOG_IN_REQUEST:
            draft.isLoggedIn = true;
            break;
        case LOG_IN_SUCCESS:
            draft.isLoggedIn = true;
            draft.accountId = action.accountId;
            draft.Account.login = action.Account.login;
            draft.isLogginIn = false;
            break;
        case LOG_IN_FAILURE:
            draft.isLoggedIn = false;
            break;
        case LOG_OUT_REQUEST:
            draft.isLogginOut = true;
            break;
        case LOG_OUT_SUCCESS:
            draft.isLoggedIn = false;
            draft.accountId = null;
            draft.token = null;
            draft.isLogginOut = false;
            break;
        case LOG_OUT_FAILURE:
            draft.isLogginOut = false;
            break;
        case GET_TOKEN:
            draft.Account.token = action.Account.token;
            break;
        case SIGN_UP_REQUEST:
            draft.isLogginIn = true;
        case SIGN_UP_SUCCESS:
            break;
        case SIGN_UP_FAILURE:
            draft.isLogginIn = false;
        default:
            break;
    }
    });
}

export default reducer;
