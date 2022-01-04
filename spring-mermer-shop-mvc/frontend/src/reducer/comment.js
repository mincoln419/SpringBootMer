import produce from "immer";

const intitialState = {
    maincomment: []
};

const reducer = (state = intitialState, action) => {
    return produce(state, (draft) => {
        switch (action.type) {
            default:
                break;
        }
    });
}

export default reducer;
