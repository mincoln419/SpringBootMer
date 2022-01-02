import React from 'react';
import { Button, Card, Image, Popover } from 'antd';
import NoticeDetail from '../pages/notices/detail';
import { useSelector } from 'react-redux';
import Avatar from 'antd/lib/avatar/avatar';
import PostImages from './PostImages';
import { EllipsisOutlined, HeartOutlined, MessageOutlined, RetweetOutlined } from '@ant-design/icons';

import { Content } from 'antd/lib/layout/layout';


const PostCard = ({notice}) => {
    const {userId} = useSelector((state) => state.user);
    return (
        <div>
            <Card
                cover={notice.images[0] && <PostImages images={notice.images}/>}
                actions={
                    [
                        <RetweetOutlined key="copyLine"/>,
                        <HeartOutlined key="hearLine"/>,
                        <MessageOutlined key="messageLine"/>,
                        <Popover key="more" content={(
                            <Button.Group>
                                {userId === notice.insterId && <Button>수정</Button>}
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
                    avatar={<Avatar>{notice.loginId}</Avatar>}
                    title = {notice.insterId}               
                    description = {notice.content}
                />
            </Card>
            {/* <CommentFrom/>
            <Comment/> */}
        </div>

    )
}

export default PostCard;