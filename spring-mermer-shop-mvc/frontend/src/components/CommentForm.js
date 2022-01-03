import { Button, Form, Input } from 'antd';
import React, { useCallback } from 'react';
import { useSelector } from 'react-redux';
import useInput from '../hooks/useInput';


const CommentForm = ({notice}) => {
    const id = useSelector((state) => state.user.accountId);
    const [commentText, onChangeInputText] = useInput('');
    const onSubmitComment = useCallback(() => {
        console.log(notice.id, commentText);
    }, [commentText]);


    return (
        <Form onFinish={onSubmitComment}>
            <Form.Item>
                <Input.TextArea
                    style={{position: 'relative', margin:0}}
                    value={commentText}
                    onChange={onChangeInputText}
                    rows={4}
                />
                <Button 
                    style={{position: 'absolute', right:8, bottom:-40}}
                    type='primary' 
                    htmlType='submit'>작성완료</Button>
            </Form.Item>

        </Form>
        
    );
};


export default CommentForm;