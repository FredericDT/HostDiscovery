import React from 'react';

import 'antd/dist/antd.css';
import {Card, Col, Image, Layout, Menu, PageHeader, Row} from 'antd';

import {HashRouter as Router, Link, Route, Switch} from 'react-router-dom';

import ReactDOM from 'react-dom';

import {useGet} from "restful-react";

import HostTable from './HostTable/index';
import NewHostForm from './NewHostForm/index';
import Home from './Home/index';


const App = () => {

    const hasLogin = () => {
        return userData && userData.userName && userData.userName.length > 0;
    };

    const {data: userData, loading: userLoading, refetch: userRefetch} = useGet({
        path: '/api/v1/user'
    });

    return (
        <Router>
            <Layout style={{height: "100vh"}}>
                <Layout.Sider>
                    <Menu>

                        <a onClick={() => {
                            window.location = '/';
                        }}>
                            <Image
                                src={"/build/logo.png"}
                                alt={"BYRIO"}
                                preview={false}
                            />
                        </a>

                        <Menu.Item>
                            <Link to={"/"}>Home</Link>
                        </Menu.Item>
                        {hasLogin() ? [
                            <Menu.Item>
                                <Link to={"/host"}>Registered Hosts</Link>
                            </Menu.Item>,
                            <Menu.Item>
                                <Link to={"/host/new"}>Register New Host</Link>
                            </Menu.Item>
                        ] : []}
                    </Menu>
                </Layout.Sider>
                <Layout>

                    <Layout.Header>
                        <PageHeader title={
                            <div
                                style={{color: 'aliceblue'}}
                            >
                                BYR HostDiscovery
                            </div>
                        }
                        />
                    </Layout.Header>


                    <Layout.Content>
                        <Row justify="center" align="middle">
                            <Col span={22}>

                                <Card>
                                    <Switch>

                                        <Route path={"/host/new"}>
                                            <NewHostForm
                                                title={"Register New Host"}
                                            />
                                        </Route>

                                        <Route exact path={"/host"}>
                                            <HostTable
                                                user={{userData, userLoading, userRefetch}}
                                                title={"Registered Hosts"}
                                            />
                                        </Route>

                                        <Route exact path={"/"}>
                                            <Home
                                                user={{userData, userLoading, userRefetch}}
                                            />
                                        </Route>

                                    </Switch>
                                </Card>

                            </Col>
                        </Row>
                    </Layout.Content>
                    <Layout.Footer
                        style={
                            {
                                textAlign: 'center'
                            }
                        }>
                        fdt @ 2021
                    </Layout.Footer>

                </Layout>
            </Layout>
        </Router>
    );

};

ReactDOM.render(
    <App/>,
    document.getElementById("react")
);