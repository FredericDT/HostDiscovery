# HostDiscovery

- 0.0.3-SNAPSHOT

## Features

- Get Host ip from Registered Hosts table.
- OIDC Login.
- Registered Hosts user group always synchronized with OIDC user group.
- Any user in OIDC user group can view/manage hosts in Registered Hosts user group.
- IPv6 supported.
- Customized labels for Host.
- Host report by HTTP(S).
- View latest 100 report logs with its report time.

## Deploy Workflow

1. Register an OIDC app at gitlab, select below scopes 
    - openid
    - email
    - profile
    
2. Deploy a MariaDB Server

3. Deploy a Redis Server

4. Configure application.yml

5. Configure webpack.config.js

6. Run `npm run build`

7. Run `mvn package`

8. Write a simple systemd unit with `java -jar HostDiscovery-0.0.3-SNAPSHOT.jar --spring.config.name=hostdiscovery`, in which, config `hostdiscovery`
 is the config file name without extension. Then start it up.

9. (Optional) Config NGINX

    ```
    server {
        listen 80;
        listen [::]:80;
    
        server_name discovery.example.com;
    
        location / {
            proxy_set_header   Host               $host;
            proxy_set_header   X-Real-IP          $remote_addr;
            proxy_set_header   X-Forwarded-Proto  $scheme;
            proxy_set_header   X-Forwarded-For    $proxy_add_x_forwarded_for;
            proxy_pass http://localhost:8080;
        }
    
    }
    ```

