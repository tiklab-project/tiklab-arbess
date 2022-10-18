server{
         listen     80;
         server_name matflow-ce.test.tiklab.net;
    location /{
             proxy_pass   http://172.11.1.18:8080;
             proxy_set_header Host  $host;
             proxy_set_header X-Real-IP $remote_addr;
             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	   proxy_set_header Upgrade $http_upgrade;
             proxy_set_header Connection "upgrade";
              }
    }

server{
         listen     80;
         server_name matflow-ee.test.tiklab.net;
    location /{
             proxy_pass   http://172.11.1.18:8081;
             proxy_set_header Host  $host;
             proxy_set_header X-Real-IP $remote_addr;
             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	   proxy_set_header Upgrade $http_upgrade;
             proxy_set_header Connection "upgrade";
              }
    }

server{
         listen     80;
         server_name matflow.test.tiklab.net;
    location /{
             proxy_pass   http://172.11.1.18:8082;
             proxy_set_header Host  $host;
             proxy_set_header X-Real-IP $remote_addr;
             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	   proxy_set_header Upgrade $http_upgrade;
             proxy_set_header Connection "upgrade";
             }
     }

server{
         listen     80;
         server_name matflow-ce.tiklab.net;
    location /{
             proxy_pass   http://172.12.1.18:8080;
             proxy_set_header Host  $host;
             proxy_set_header X-Real-IP $remote_addr;
             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	   proxy_set_header Upgrade $http_upgrade;
             proxy_set_header Connection "upgrade";
              }
    }

server{
         listen     80;
         server_name matflow-ee.tiklab.net;
    location /{
             proxy_pass   http://172.12.1.18:8081;
             proxy_set_header Host  $host;
             proxy_set_header X-Real-IP $remote_addr;
             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	   proxy_set_header Upgrade $http_upgrade;
             proxy_set_header Connection "upgrade";
              }
    }