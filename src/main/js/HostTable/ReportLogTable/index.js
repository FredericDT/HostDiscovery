import {Table, Typography} from "antd";
import React from "react";
import {useGet} from "restful-react";

const ReportLogTable = ({hostId, title}) => {

    const {data: reportLog, loading, refetch} = useGet({
        path: `/api/v1/host/${hostId}/reportLog`
    });

    return (
        <Table
            dataSource={reportLog ? reportLog : []}
            bordered={true}
            loading={!reportLog || loading}
            title={
                () =>
                    <Typography.Title
                        level={2}
                    >
                        {title}
                    </Typography.Title>
            }
            rowKey='id'
        >
            <Table.Column
                title={"id"}
                dataIndex={"id"}
                key={"reportLog-id"}
            />

            <Table.Column
                title={"hostname"}
                dataIndex={"hostname"}
                key={"hostname"}
            />

            <Table.Column
                title={"ip"}
                dataIndex={"ip"}
                key={"ip"}
            />

            <Table.Column
                title={"time"}
                dataIndex={"time"}
                key={"time"}
                render={
                    (text, record, index) => {
                        return text !== null && text.length > 0 ? new Date(text).toLocaleString(): '';
                    }
                }
            />

        </Table>
    );
};

export default ReportLogTable;