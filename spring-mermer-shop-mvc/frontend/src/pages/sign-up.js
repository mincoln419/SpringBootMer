import React from 'react';
React.useLayoutEffect = React.useEffect;
import { Form, Input, InputNumber, Button, Divider, Typography, Space , Checkbox } from 'antd';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import {signUpRequestAction} from '../reducer/user';
import { useRouter } from 'next/router'
import wrapper from '../store/configureStore';
import { LOG_IN_STATE_UPDATE } from '../actions';
import { END } from 'redux-saga';
const { Title, Paragraph, Text } = Typography;
const layout = {
    labelCol: {
      span: 5,
    },
    wrapperCol: {
      span: 16,
    },
  };
  /* eslint-disable no-template-curly-in-string */
  
const validateMessages = {
    required: '${label} is required!',
    types: {
      email: '${label} is not a valid email!',
      number: '${label} is not a valid number!',
    },
    number: {
      range: '${label} must be between ${min} and ${max}',
    },
  };
  /* eslint-enable no-template-curly-in-string */
  

  const options = [
    { label: 'Administration', value: 'ADMIN' },
    { label: 'User', value: 'USER' }
  ];

  function onChange(checkedValues) {
    console.log('checked = ', checkedValues);
  }


const SignUp = () => {
  const router = useRouter();
  const dispatch = useDispatch();
  
  const onFinish = (values) => {
    dispatch(signUpRequestAction(values));
        
  };
    
      return (
        <>
        <Title>회원가입</Title>
        <Divider style={{ borderWidth: 2, borderColor: 'black' }} />
        <Form {...layout} name="nest-messages" onFinish={onFinish} validateMessages={validateMessages}>
          <Form.Item
            name={['user', 'login']}
            label="Login ID"
            rules={[
              {
                required: true,
              },
            ]}
          >
             <Input name={['user', 'login']}/>
            </Form.Item>
           <Form.Item
        name={['user', "pass"]}
        label="Password"
        rules={[
          {
            required: true,
            message: 'Please input your password!',
          },
        ]}
        hasFeedback
      >
        <Input.Password name={['user', "pass"]}/>
      </Form.Item>

      <Form.Item
        name="confirm"
        label="Confirm Password"
        dependencies={['pass']}
        hasFeedback
        rules={[
          {
            required: true,
            message: 'Please confirm your password!',
          },
          ({ getFieldValue }) => ({
            validator(_, value) {
              if (!value || getFieldValue(['user', "pass"]) === value) {
                return Promise.resolve();
              }

              return Promise.reject(new Error('The two passwords that you entered do not match!'));
            },
          }),
        ]}
      >
        <Input.Password name={['confirm']} />
      </Form.Item>
      <Form.Item
            name={['user', 'username']}
            label="User Name"
            rules={[
              {
                required: true,
              },
            ]}
          >
             <Input />
            </Form.Item>
          <Form.Item
            name={['user', 'email']}
            label="Email"
            rules={[
              {
                type: 'email',
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name={['user', 'hpNum']}
            label="phone"
            rules={[
              {
                max:11
              },
            ]}
          >
            <Input />
          </Form.Item>
          
          <Form.Item
            name={['user', 'role']}
            label="Role"
          >
          <Checkbox.Group options={options} defaultValue={['USER']} onChange={onChange} />
          <br />
          <br /> 

          </Form.Item>

          <Form.Item wrapperCol={{ ...layout.wrapperCol, offset: 8 }}>
            <Button type="primary" htmlType="submit">
              Submit
            </Button>
          </Form.Item>
        </Form>
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

export default SignUp;