import {Form, Input, Select, Space, Table, Tag, Typography, Alert} from "antd";
import React from "react";
import {useGet} from "restful-react";

import axios from 'axios';

import PopoverDeleteConfirm from './PopoverDeleteConfirm';
import ReportLogTable from './ReportLogTable/index';

const HostTable = ({title, user}) => {

    const {data: host, loading, refetch} = useGet({
        path: '/api/v1/host'
    });

    const [editingHostId, setEditingHostId] = React.useState(null);
    const [alerts, setAlerts] = React.useState([]);

    const [form] = Form.useForm();

    const handleEdit = (record) => {

        const hostId = record.id;
        const updatedKVs = form.getFieldsValue(true);

        const updatedKeys = Object.keys(updatedKVs);

        if (updatedKeys.length > 0) {

            for (var key in updatedKVs) {
                record[key] = updatedKVs[key];
            }

            axios.post(
                `/api/v1/host/${hostId}`,
                record
            ).then((res) => {
                setAlerts([
                    <Alert
                        message={`Host ID: ${hostId} Updated!`}
                        type="success"
                        showIcon
                        closable
                    />
                    , ...alerts
                ]);
                refetch();
            }).catch((err) => {
                setAlerts([
                    <Alert
                        message={`Error on Host ID: ${hostId}!`}
                        description={err.response.data.message}
                        type="error"
                        showIcon
                        closable
                    />
                    , ... alerts
                ]);
            });
        }

        setEditingHostId(null);
    };

    return (
        <Form form={form}>
            {alerts}
            <Table
                dataSource={host}
                bordered={true}
                loading={!host || loading}
                title={
                    () => (
                        <Typography.Title>
                            {title}
                        </Typography.Title>
                    )
                }
                expandable={{
                    expandedRowRender: record => {

                        const curlCommand = `curl -X POST "${location.protocol}//${location.host}/api/v1/host/register/${record.id}" -H "accept: */*" -H "Content-Type: application/json" -d "{\\"hostname\\": \\"$(hostname)\\"}"`;

                        return (
                            <div>
                                <Space
                                    direction={"vertical"}
                                >
                                    <Typography.Text>
                                        Add the following command to crontab, adjust intervals as you wish.
                                    </Typography.Text>
                                    <Typography.Text
                                        code={true}
                                        copyable={true}
                                    >
                                        {curlCommand}
                                    </Typography.Text>

                                    <ReportLogTable
                                        hostId={record.id}
                                        title={"Report Log"}
                                    />

                                </Space>
                            </div>
                        );
                    },
                    rowExpandable: record => true,
                }}
                rowKey='id'
            >
                <Table.Column
                    title="id"
                    dataIndex="id"
                    key="host-id"
                />
                <Table.Column
                    title="custom label"
                    dataIndex="customLabel"
                    key="customLabel"
                    render={
                        (text, record, index) => {
                            return editingHostId !== record.id ?
                                <div>
                                    {text}
                                </div>
                                :
                                <div>
                                    <Form.Item
                                        name={'customLabel'}
                                    >
                                        <Input.TextArea defaultValue={text}/>
                                    </Form.Item>
                                </div>
                                ;
                        }
                    }
                />
                <Table.Column
                    title="user group"
                    dataIndex="userGroupList"
                    key="userGroupList"
                    render={
                        (userGroupList, record, index) => (
                            editingHostId !== record.id ?
                                <div>
                                    {userGroupList.map(principal => (
                                        <Tag color="blue" key={"userGroupList-" + principal}>
                                            {principal}
                                        </Tag>
                                    ))}
                                </div>
                                :
                                <div>
                                    {user.userData ?
                                        <Form.Item
                                            name={"userGroupList"}
                                        >
                                            <Select
                                                mode={"multiple"}
                                                allowClear
                                                placeholder={"Select User Group"}
                                                defaultValue={userGroupList}
                                            >
                                                {
                                                    user.userData.userGroupList.map(item => (
                                                        <Select.Option
                                                            key={item}
                                                        >
                                                            {item}
                                                        </Select.Option>
                                                    ))
                                                }
                                            </Select>
                                        </Form.Item>
                                        : []
                                    }
                                </div>
                        )
                    }
                />
                <Table.ColumnGroup title="last report log">
                    <Table.Column
                        title="hostname"
                        dataIndex={["lastReportLog", "hostname"]}
                        key="lastReportLog-hostname"
                    />
                    <Table.Column
                        title="ip"
                        dataIndex={["lastReportLog", "ip"]}
                        key="lastReportLog-ip"
                    />
                    <Table.Column
                        title="time"
                        dataIndex={["lastReportLog", "time"]}
                        key="lastReportLog-time"
                        render={
                            (text, record, index) => {
                                return text !== null && text.length > 0 ? new Date(text).toLocaleString(): '';
                            }
                        }
                    />
                </Table.ColumnGroup>
                <Table.Column
                    title="action"
                    key="action"
                    render={
                        (text, record) => (
                            <Space size="middle">
                                {editingHostId !== record.id ?
                                    <a
                                        onClick={() => {
                                            setEditingHostId(record.id);
                                        }}
                                    >
                                        Edit
                                    </a>
                                    : [
                                        <a
                                            onClick={() => {
                                                handleEdit(record);
                                            }}
                                        >
                                            Save
                                        </a>,
                                        <a
                                            onClick={() => {
                                                setEditingHostId(null);
                                            }}
                                        >
                                            Cancel
                                        </a>
                                    ]
                                }
                                <PopoverDeleteConfirm
                                    hostId={record.id}
                                    refetch={refetch}
                                />
                            </Space>
                        )
                    }
                />
            </Table>
        </Form>
    );
};

export default HostTable;
