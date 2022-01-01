export const intitialState = {
    isLoggedIn : false,
    username: null,
    signUpData :{},
    loginData : {},
    token : ""
};
/* Action Creator */
export const loginAction = (data) => {
    return {
        type: 'LOG_IN',
        data,
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
                username: action.username
            };

        case 'LOG_OUT':
            return {
                ...state,
                isLoggedIn: false,
                username: null
            };
        default:
            return state;
    }
    
}

export default reducer;
