import produce from "immer";
import shortid from "shortid";
import faker from "faker";
import { QUERY_NOTICE_FAILURE, QUERY_NOTICE_REQUEST, QUERY_NOTICE_SUCCESS } from "../actions/notice";

const intitialState = {
    mainPosts: [{
        id: 1,
        title:"test1 - 타이틀",
        insterId: 1,
        contents: "본문입니다 #나는 #은우의 #아빠다",
        Replies:[{
            id: 1,
            insterId:2,
            contents:"댓글입니다 #나는 #은우의 #아빠다 #댓글이다"
        }],
        images:[{
            src: "https://mblogthumb-phinf.pstatic.net/20140417_50/skineye11_1397707857159Xd00q_JPEG/naver_com_20140417_130214.jpg?type=w2"
        },
        {
            src: "https://w.namu.la/s/67552ce204c3a7be54a6b5376501d43babb21da14b6c58ead22470f769b56543856d39e62e68056dcaba89fa62862d6e02134d361aa5547f2387e7606d8ecee1a62d3320843174e63230d0e816ac2d6438ac05268206efc054f419abfbd3a52b"
        },
        {
            src: "https://i.imgur.com/IIqdLKX.jpg"
        }
        ],
    }],
    noticeList:[{
            id: 1,
            title:"test1 - 타이틀",
            insterId: 1,
            instDtm: new Date(2021, 12, 26, 12, 11, 25),
        }
    ],
    images: [],
    noticeAdded: false,
    loading: false
};

intitialState.mainPosts = intitialState.mainPosts.concat(
    Array(20).fill().map(() => ({
        id: shortid.generate(),
        insterId: shortid.generate(),
        title: faker.lorem.paragraph(),
        contents: faker.lorem.paragraph(),
        images: [{
            src : faker.image.image()
        }],
        Replies: [{
            id: shortid.generate(),
            insterId: shortid.generate(),
            contents: faker.lorem.sentence(),
        }],

    }))
)
;


const ADD_POST = 'ADD_POST';

export const addPost = {
    type: ADD_POST,
}

const dummyPost = {
    id:2,
    title: "추가 제목",
    insterId:1,
    contents: "추가 내용",
    images:[],
    Replies:[]
}

export const queryNoticeRequestAction = () => {
    return {
        type: QUERY_NOTICE_REQUEST,
        loading: true
    }
}

const reducer = (state = intitialState, action) => {

    return produce(state, (draft) => {
    switch (action.type) {
        case ADD_POST:
            draft.mainPosts = dummyPost;
            draft.noticeAdded = true;
            break;
        case QUERY_NOTICE_REQUEST:
            draft.loading = true;
            break;
        case QUERY_NOTICE_SUCCESS:
            draft.noticeList = action.data;
            draft.loading = false;
            break;
        case QUERY_NOTICE_FAILURE:
            draft.loading = false;
            break;
        default:
            break;
    }
    });
}

export default reducer;
