const intitialState = {
    isLoggedIn : true,
    loginId: null,
    signUpData :{},
    loginData : {},
    token : null
};
/* Action Creator */
export const loginAction = (data) => {
    return {
        type: 'LOG_IN',
        data,
    }
};

export const getToken = () => {
    return {
        type: 'GET_TOKEN',
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
                ...state.user,
                isLoggedIn: true,
                loginId: action.username
            };

        case 'LOG_OUT':
            return {
                ...state,
                isLoggedIn: false,
                loginId: null,
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
