import React from 'react';
React.useLayoutEffect = React.useEffect;
import { Form, Input, Button, Checkbox, Typography, Divider } from 'antd';
import Index from '.';
import axios from 'axios';
const { Title} = Typography;
import { useDispatch, useSelector } from 'react-redux';
import { loginAction, getToken } from '../reducer/user';
import { useRouter } from 'next/router'

const Login = () => {
    const dispatch = useDispatch();
    const router = useRouter();
    const {isLoggedIn} = useSelector((state) => state.user);
    const notice = useSelector((state) => state.notice);
    console.log("isLoggedIn", isLoggedIn);
    console.log("notice", notice);
    const onFinish = (data) => {//이미 e.preventDefault 적용이 되어있음.
      data.grant_type = 'password';
      const token = 'Basic ' + window.btoa("" + ':' + ""); //basic auth 생성 공통 프로토콜
      const name = data.loginId;

      axios.request({
        'url': "/oauth/token",
        'method': "post",
        'baseURL': "/",
        'headers': {'Content-Type' : 'form-data'},
        'auth': {//Basic Auth를 만들어주는 해더부분임
          'username': 'merApp0203041910112',
          'password': 'mermer110129345671'
        },
        'params' : {
            'username': data.loginId,
            'password': data.password, //비밀번호 input암호화 필요 -> 백엔드 서버에서 다시한번 암호화 해서 DB에 저장
            'grant_type': 'password'
        }
      }).then(function(res) {
        const token = res.data.access_token;
        dispatch(getToken({token}));
        /* 로그인 아이디로 account_id를 가져와 reducer에 담음*/
        axios.request({
          url: "/api/account/login/"+ data.loginId,
          method: "get",
          baseURL: "/",
          headers: {'Content-Type' : 'application/json;charset=UTF-8',
                    'Accept':'application/hal+json',
                    'Authorization' : 'Bearer ' + token
                }
        }).then((resCall) => {
          const accountId = resCall.data.id;
          dispatch(loginAction({accountId}));
         // router.push("/");
        });
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
  

export default Login;