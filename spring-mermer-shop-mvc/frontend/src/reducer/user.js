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
    switch (action.type) {
        case LOG_IN_REQUEST:
            return {
                ...state,
                isLogginIn: true
            };
        case LOG_IN_SUCCESS:
            return {
                ...state,
                isLoggedIn: true,
                accountId: action.accountId,
                isLogginIn: false
            };
        case LOG_IN_FAILURE:
            return {
                ...state,
                isLogginIn: false
            };
        case LOG_OUT_REQUEST:
            return {
                ...state,
                isLogginOut: true
            };
        case LOG_OUT_SUCCESS:
            return {
                ...state,
                isLoggedIn: false,
                accountId: null,
                token: null,
                isLogginOut: false
            };
        case LOG_OUT_FAILURE:
            return {
                ...state,
                isLogginOut: false
            };
        case GET_TOKEN:
            console.log(action);
            return {
                ...state,
                Account: {...state.Account, token:action.Account.token}
            };
        case SIGN_UP_REQUEST:
            return {
                ...state,
                isLogginIn: true
            };
        case SIGN_UP_SUCCESS:
            return {
                ...state,
                Account: action.Account,
            };
        case SIGN_UP_FAILURE:
            return {
                ...state,
                isLogginIn: false
            };

        default:
            return state;
    }
    
}

export default reducer;
