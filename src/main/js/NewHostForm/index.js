import {Button, Form, Input, Result, Select, Spin, Typography} from "antd";
import React from "react";
import {useGet} from "restful-react";

import {Link} from 'react-router-dom';

import axios from 'axios';

const Index = ({title}) => {

    const [finish, setFinish] = React.useState(false);
    const [requestLoading, setRequestLoading] = React.useState(false);

    const {data: user, loading, refetch} = useGet({
        path: '/api/v1/user'
    });

    const [requestResponse, setRequestResponse] = React.useState({});

    const onFinish = (values) => {
        setRequestLoading(true);
        axios.put('/api/v1/host', values).then(
            (res) => {
                setRequestResponse({
                    status: 'success',
                    title: 'Successfully Registered a Host',
                    subTitle: 'Host ID: ' + res.data.id + ' registered.'
                });
            }
        ).catch(error => {
            setRequestResponse({
                status: '500',
                title: 'Oops!',
                subTitle: error.response.data.message
            });
        }).then(() => {
            setFinish(true);
            setRequestLoading(false);
        });
    };

    return (
        finish ?
            <Result
                status={requestResponse.status}
                title={requestResponse.title}
                subTitle={requestResponse.subTitle}
                extra={[
                    <Link to={'/host'}>
                        <Button type={"primary"}>
                            Go Registered Hosts
                        </Button>
                    </Link>,
                ]}
            />
            :
            <Spin spinning={requestLoading || !user || loading}>
                {user && !loading ?
                    <Form 
                        onFinish={onFinish}
                        initialValues={
                            {
                                userGroupList: [user.userName]
                            }
                        }
                    >
                        <Typography.Title>
                            {title}
                        </Typography.Title>
                        <Form.Item
                            label={"CustomLabel"}
                            name={"customLabel"}
                            placeholder={"Input Custom Label"}
                        >
                            <Input/>
                        </Form.Item>
                        <Form.Item
                            label={"UserGroup"}
                            name={"userGroupList"}
                        >
                            <Select
                                mode={"multiple"}
                                allowClear
                                placeholder={"Select User Group"}
                            >
                                {
                                    (user ? user.userGroupList : []).map(item => (
                                        <Select.Option
                                            key={item}
                                        >
                                            {item}
                                        </Select.Option>
                                    ))
                                }
                            </Select>
                        </Form.Item>
                        <Form.Item>
                            <Button
                                type={"primary"}
                                htmlType={"submit"}
                            >
                                Submit
                            </Button>
                        </Form.Item>
                    </Form>
                    : ''}
            </Spin>

    );
};

export default Index;