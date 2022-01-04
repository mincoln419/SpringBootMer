const intitialState = {
    toggleMenu : false,
    toggleBar : false
};

export const setToggleMenuAction = (flag) => {
    return {
        type: 'SET_TOGGLE_MENU',
        flag: flag
    }
}

export const setToggleBarAction = (flag) => {
    return {
        type: 'SET_TOGGLE_BAR',
        flag: flag
    }
}

const reducer = (state = intitialState, action) => {
    switch (action.type) {
        case 'SET_TOGGLE_MENU':
            return {
                ...state,
                toggleMenu: action.flag
            };
        case 'SET_TOGGLE_BAR':
            return {
                ...state,
                toggleBar: action.flag
            };
        default:
            return state;
    }
    
}

export default reducer;
