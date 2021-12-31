import React from 'react';
React.useLayoutEffect = React.useEffect;
import { Form, Input, Button, Checkbox, Typography, Divider } from 'antd';
import Index from '.';
import axios from 'axios';
const { Title} = Typography;
const {useEffect, useState} = require('react');

const Login = ({setIsLoggedIn}) => {

    const onFinish = (data) => {//이미 e.preventDefault 적용이 되어있음.
        data.grant_type = 'password';
      console.log('Success:', data);

      const token = 'Basic ' + window.btoa("" + ':' + ""); //basic auth 생성 공통 프로토콜
      console.log(token);
      axios.request({
        url: "/oauth/token",
        method: "post",
        baseURL: "/",
        headers: {'Content-Type' : 'form-data'},
        auth: {//Basic Auth를 만들어주는 해더부분임
          username: 'merApp0203041910112',
          password: 'mermer110129345671'
        },
        params : {
            username: data.username,
            password: data.password,
            grant_type: 'password'
        }
      }).then(function(res) {
        console.log(res);
        useState.accessToken
        setIsLoggedIn(true);
      });

    }
  
    const onFinishFailed = (errorInfo) => {
      console.log('Failed:', errorInfo);
    };
  
    return (
      <>
        {false ? <Index/> :
      <Form
        name="basic"
        labelCol={{
          span: 7,
        }}
        wrapperCol={{
          span: 10,
        }}
        initialValues={{
          remember: true,
        }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
      >
        <Typography>
        <Title>로그인</Title>
        <Divider style={{ borderWidth: 2, borderColor: 'black' }} />
        </Typography>
        
        <Form.Item
          label="Username"
          name="username"
          autoComplete="on"
          rules={[
            {
              required: true,
              message: 'Please input your username!',
            },
          ]}
        >
          <Input />
        </Form.Item>
  
        <Form.Item
          label="Password"
          name="password"
          autoComplete="on"
          rules={[
            {
              required: true,
              message: 'Please input your password!',
            },
          ]}
        >
          <Input.Password />
        </Form.Item>
  
        <Form.Item
          name="remember"
          valuePropName="checked"
          wrapperCol={{
            offset: 7,
            span: 16,
          }}
        >
          <Checkbox>Remember me</Checkbox>
        </Form.Item>
  
        <Form.Item
          wrapperCol={{
            offset: 7,
            span: 16,
          }}
        >
          <Button type="primary" htmlType="submit">
            Submit
          </Button>
        </Form.Item>
      </Form>
        }
      </>
    );
  };
  

export default Login;