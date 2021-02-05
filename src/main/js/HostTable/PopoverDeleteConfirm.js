import {Button, Card, Popover, Result, Row, Spin} from "antd";
import {DeleteOutlined} from '@ant-design/icons';
import React from "react";

import axios from 'axios';

const PopoverDeleteConfirm = ({hostId, refetch}) => {
    const [display, setDisplay] = React.useState(false);

    const handleVisibleChange = visible => {
        setDisplay(visible);
    };

    const [finish, setFinish] = React.useState(false);
    const [requestLoading, setRequestLoading] = React.useState(false);

    const [requestResponse, setRequestResponse] = React.useState({
        status: 'success',
        title: 'Successfully Deleted a Host',
        subTitle: 'Host ID: ' + hostId + ' deleted.'
    });

    const handleConfirm = () => {
        setRequestLoading(true);
        axios.delete(`/api/v1/host/${hostId}`).then(
            (res) => {
                setTimeout(() => {
                    refetch();
                    setFinish(false);
                }, 2000);
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
        <Popover
            visible={display}
            onVisibleChange={handleVisibleChange}
            content={

                <Card>
                    <Spin
                        spinning={requestLoading}
                    >
                        {
                            finish ?
                                <Result
                                    status={requestResponse.status}
                                    title={requestResponse.title}
                                    subTitle={requestResponse.subTitle}
                                />
                                :
                                <div>
                                    <Row>
                                        <DeleteOutlined/>
                                        Are you sure to delete host {hostId}?
                                    </Row>
                                    <Row>
                                        <Button
                                            type={'primary'}
                                            onClick={handleConfirm}
                                        >
                                            Yes
                                        </Button>
                                        <Button
                                            onClick={
                                                () => {
                                                    setDisplay(false);
                                                }
                                            }
                                        >
                                            No
                                        </Button>
                                    </Row>
                                </div>
                        }
                    </Spin>
                </Card>
            }
        >
            <a>Delete</a>
        </Popover>

    );
};

export default PopoverDeleteConfirm;