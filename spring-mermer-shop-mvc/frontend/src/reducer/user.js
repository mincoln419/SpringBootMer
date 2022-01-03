import { GET_TOKEN, LOG_IN_FAILURE, LOG_IN_REQUEST, LOG_IN_SUCCESS, LOG_OUT_FAILURE, LOG_OUT_REQUEST, LOG_OUT_SUCCESS } from "../actions";

const intitialState = {
    isLoggedIn : false,
    accountId: null,
    signUpData :{},
    loginData : {},
    token : null,
    isLogginIn : false,
    isLogginOut : false,
    loginId: null,
    password: null,
};
/* Action Creator */
export const loginRequestAction = (data) => {
    console.log("LOG_IN_REQUEST", LOG_IN_REQUEST);
    return {
        type: LOG_IN_REQUEST,
        loginId: data.loginId,
        password: data.password
    }
};

export const logoutAction = () => {
    return {
        type: LOG_OUT_REQUEST,
    }
};

const reducer = (state = intitialState, action) => {
    console.log("reducer work?" + action.type);
    switch (action.type) {
        case LOG_IN_REQUEST:
            console.log("login Reducer?");
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
            alert("reducer -logout");
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
            return {
                ...state,
                token: action.token
            };
        default:
            return state;
    }
    
}

export default reducer;
