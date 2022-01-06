import { Button, Form, Input } from 'antd';
import React, { useCallback, useRef, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { addPost } from '../reducer/notice';
import { DownOutlined , UserOutlined} from '@ant-design/icons';
import {UPLOAD_IMAGE_REQUEST} from '../actions/notice'

const NoticeForm = () => {
    const {images} = useSelector((state) => state.notice);
    const {token} = useSelector((state) => state.user.Account);
    console.log("token", token);
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

    //업로드 버튼 눌렀을 때 imageInput에 값 넣기 -> array로 
    const onClickImageUpload = useCallback(()=>{
        imageInput.current.click();
    }, [imageInput.current]);

    //업로드 화면에서 파일 선택했을 때 formData에 담아 upload -> 미리보기로 리사아즈
    const onChangeImages = useCallback((e) => {
        const imageFormData = new FormData();
        console.log(e.target.files);
        [].forEach.call(e.target.files, (f) => {
            imageFormData.append('images', f);
        });
        imageFormData.append('name', 'notice');

        dispatch({
            type: UPLOAD_IMAGE_REQUEST,
            tmpImgs: imageFormData,
            token : token,
        });
    });

    return (

        <Form sytle={{ margin: '10px 0 20px'}} encType='multipart/form-data' onFinish={onSubmit}>

            <Input.TextArea
                value = {text}
                onChange={onChangeText}
                maxLength={140}
                placeholder='내용을 입력하세요'
            />
            <div>
                <input type="file" multiple hidden ref={imageInput} onChange ={onChangeImages}/>
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