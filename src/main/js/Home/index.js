import React from "react";

import {Button, Card, Typography} from 'antd';

const Home = ({user}) => {

    return (
        <Card>
            {user.userData && user.userData.userName ?
                <div>
                    <Typography.Title>
                        Greetings, {user.userData.userName}!
                    </Typography.Title>
                    <Button
                        type={"primary"}
                    >
                        <a href={"/logout"}>
                            Logout
                        </a>
                    </Button>
                </div>
                :
                <div>
                    <Button
                        type={"primary"}
                    >
                        <a href={"/oauth2/authorization/byr-gitlab"}>
                            Login with BYR Gitlab (git.byr.moe)
                        </a>
                    </Button>
                </div>
            }
        </Card>
    );
};

export default Home;