/* 초기 데이터 구조를 잡아놔야 한다 */
const intitialState = {
    user: {
        isLoggedIn : false,
        username: null,
        signUpData :{},
        loginData : {},
        token : ""
    },
    post:{
        mainPosts: []
    }
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


/* 비동기 action creator */
// (이전상태, 액션) => 다음상태
const rootReducer = (state = intitialState, action)=>{

    switch (action.type) {
        case 'LOG_IN':
            return {
                ...state,
                user: {
                    ...state.user,
                    isLoggedIn: true,
                    username: action.username
                }
            }
        case 'LOG_OUT':
            return {
                ...state,
                user: {
                    ...state.user,
                    isLoggedIn: false,
                    username: null
                }
            }
            default: {
                return {
                   ...state,
                  };
            }
    }
};

export default rootReducer;