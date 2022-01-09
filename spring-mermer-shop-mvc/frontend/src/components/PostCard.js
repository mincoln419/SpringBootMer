import React, { useCallback, useState } from 'react';
import { Button, Card, Comment, Image, List, Popover } from 'antd';
import NoticeDetail from '../pages/notices/[id]';
import { useSelector } from 'react-redux';
import Avatar from 'antd/lib/avatar/avatar';
import PostImages from './PostImages';
import { EllipsisOutlined, HeartOutlined, MessageOutlined, RetweetOutlined, HeartTwoTone} from '@ant-design/icons';

import { Content } from 'antd/lib/layout/layout';
import CommentForm from './CommentForm';
import PostCardContent from './postCardContent';


const PostCard = ({notice}) => {
    const {accountId, token} = useSelector((state) => state.user);

    //TODO 실제 데이터 처리로 대체예정
    const [isLinked, setIsLinked] =  useState(false);
    const [commentFormOpen, setCommentFormOpen] =  useState(false);

    
    const onToggleLike = useCallback(() => {
        setIsLinked((prev) => !prev);
    }, []);
    const onCommentFormOpened = useCallback(() => {
        setCommentFormOpen((prev) => !prev);
    }, []);

    
    return (
        <div>
            <Card
                cover={notice.images[0] && <PostImages images={notice.images}/>}
                actions={
                    [
                        <RetweetOutlined key="copyLine"/>,
                        isLinked ? <HeartTwoTone twoToneColor="#eb2f96" key= "heart" onClick={onToggleLike}/> : <HeartOutlined key="hearLine" onClick={onToggleLike}/>
                        ,
                        <MessageOutlined key="messageLine" onClick={onCommentFormOpened}/>,
                        <Popover key="more" content={(
                            <Button.Group>
                                {accountId === notice.insterId && <Button>수정</Button>}
                                <Button type="danger">삭제</Button>
                                <Button>신고</Button>
                            </Button.Group>
                        )}>
                            <EllipsisOutlined/>
                        </Popover>
                    ]
                }
            >
                <Image></Image>
                <Content></Content>
                <Button></Button>

                <Card.Meta
                    avatar={<Avatar>{notice.insterId}</Avatar>}
                    title = {notice.insterId}               
                    description = {<PostCardContent postData = {notice.contents} />}
                />
            </Card>
            {commentFormOpen  && (<div>
                <CommentForm notice={notice}/>
                <List
                    header={`${notice.Replies.length}개의 댓글`}
                    itemLayout='horizontal'
                    dataSource={notice.Replies}
                    renderItem={(item) => (
                        <li>
                            <Comment
                                author={item.insterId}
                                avatar={<Avatar>{item.insterId}</Avatar>}
                                content={<PostCardContent postData = {item.contents} />}
                            />
                        </li>
                    )}
                />
            </div>)}
        </div>

    )
}

export default PostCard;