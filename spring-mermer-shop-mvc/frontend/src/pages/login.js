import React from 'react';
React.useLayoutEffect = React.useEffect;
import { Form, Input, Button, Checkbox, Typography, Divider } from 'antd';
import Index from '.';
const { Title} = Typography;
import { useDispatch, useSelector } from 'react-redux';
import {  loginRequestAction } from '../reducer/user';
import { useRouter } from 'next/router'
import { useCookies } from 'react-cookie';
import wrapper from '../store/configureStore';
import { END } from 'redux-saga';
import { LOG_IN_STATE_UPDATE } from '../actions';

const Login = () => {
    const dispatch = useDispatch();
    const router = useRouter();
    const {isLoggedIn} = useSelector((state) => state.user);
    const loginInfo = ['token' ,"loginId", "accountId"];
    const [cookies, setCookie, rmCookie] = useCookies(loginInfo);

    if(isLoggedIn){
      router.push("/");
    }
    const onFinish = (data) => {//이미 e.preventDefault 적용이 되어있음.
      dispatch(loginRequestAction(data, setCookie));
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
          label="Login Id"
          name="loginId"
          autoComplete="on"
          rules={[
            {
              required: true,
              message: 'Please input your Login Id!',
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
  
  //리덕스에 데이터가 채워진상태로 랜더링된다.
  export const getServerSideProps = wrapper.getServerSideProps(async (context)=>{
    
    const cookies = context.req.cookies;
    const state =  context.store.getState();

    if(!state.user.isLoggedIn && cookies.loginId){
      context.store.dispatch(
        {
            type: LOG_IN_STATE_UPDATE,
            accountId: cookies.accountId,
            token: cookies.token,
            login: cookies.loginId
        }
    );
    }
 
    context.store.dispatch(END);
    await context.store.sagaTask.toPromise();    
  });

export default Login;