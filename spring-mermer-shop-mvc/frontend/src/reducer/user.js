const intitialState = {
    isLoggedIn : true,
    accountId: null,
    signUpData :{},
    loginData : {},
    token : null
};
/* Action Creator */
export const loginAction = (data) => {
    return {
        type: 'LOG_IN',
        accountId: data.accountId
    }
};

export const getToken = (data) => {
    return {
        type: 'GET_TOKEN',
        token: data.token
    }
};

export const logoutAction = () => {
    return {
        type: 'LOG_OUT',
    }
};

const reducer = (state = intitialState, action) => {

    switch (action.type) {
        case 'LOG_IN':
            return {
                ...state,
                isLoggedIn: true,
                accountId: action.accountId
            };

        case 'LOG_OUT':
            return {
                ...state,
                isLoggedIn: false,
                accountId: null,
                token: null
            };
        case 'GET_TOKEN':
            return {
                ...state,
                token: action.token
            };
        default:
            return state;
    }
    
}

export default reducer;
