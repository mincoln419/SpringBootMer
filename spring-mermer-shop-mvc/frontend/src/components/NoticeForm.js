import { Button, Form, Input } from 'antd';
import React, { useCallback, useRef, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { addPost } from '../reducer/notice';
import { DownOutlined , UserOutlined} from '@ant-design/icons';

const NoticeForm = () => {
    const {images} = useSelector((state) => state.notice);
    const [text, setText] = useState('');
    const dispatch = useDispatch();
    const imageInput = useRef();

    const onChangeText = useCallback((e) => {
        setText(e.target.value);
    });
    const onSubmit = useCallback(() => {
        dispatch(addPost);
        setText('');
    }, []);

    const onClickImageUpload = useCallback(()=>{
        imageInput.current.click((e) => console.log(e));
    }, [imageInput.current]);

    return (

        <Form sytle={{ margin: '10px 0 20px'}} encType='multipart/form-data' onFinish={onSubmit}>

            <Input.TextArea
                value = {text}
                onChange={onChangeText}
                maxLength={140}
                placeholder='내용을 입력하세요'
            />
            <div>
                <input type="file" multiple hidden ref={imageInput}/>
                <Button onClick={onClickImageUpload}>이미지 업로드</Button>
                <Button type="primary" style={{float:'right'}} htmlType="submit">작성완료</Button>

            </div>
            <div>
                {images.map((v) => (
                  <div key={v} style={{display : 'inline-block'}}>
                    <img src={v} style={{width: '200px'}} alt={v} />
                    <div>
                        <Button>제거</Button>
                    </div>
                  </div>  
                ))}
            </div>
        </Form>

    );


}

export default NoticeForm;