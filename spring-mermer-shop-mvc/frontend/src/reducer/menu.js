import produce from "immer";

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
    return produce(state, (draft) => {
    switch (action.type) {
        case 'SET_TOGGLE_MENU':
            draft.toggleMenu = action.flag;
            break;
        case 'SET_TOGGLE_BAR':
            draft.toggleBar = action.flag;
            break;
        default:
            break;
    }
});
}

export default reducer;
