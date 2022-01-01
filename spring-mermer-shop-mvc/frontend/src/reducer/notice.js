const intitialState = {
    mainPosts: [{
        id: 1,
        title:"test1 - 타이틀",
        insterId: 1,
        contents: "test Content",
        reply:[{
            insterId:2,
            contents:"댓글입니다"
        }],
        images:[{
            src: "https://mblogthumb-phinf.pstatic.net/20140417_50/skineye11_1397707857159Xd00q_JPEG/naver_com_20140417_130214.jpg?type=w2"
        },
        {
            src: "http://upload2.inven.co.kr/upload/2019/08/05/bbs/i15410277744.jpg"
        },
        ],
    }],
    images: [],
    noticeAdded: false
};

const ADD_POST = 'ADD_POST';

export const addPost = {
    type: ADD_POST,
}

const dummyPost = {
    id:2,
    title: "추가 제목",
    insterId:2,
    contents: "추가 내용",
    images:[],
    reply:[]
}

const reducer = (state = intitialState, action) => {
    switch (action.type) {
        case ADD_POST:
            return {
                ...state,
                mainPosts: [dummyPost, ...state.mainPosts],
                noticeAdded: true
            };
        default:
            return state;
    }
    
}

export default reducer;
