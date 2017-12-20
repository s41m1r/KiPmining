#!/bin/bash
service mysql stop
ssh -L 3306:web.ghtorrent.org:3306 ghtorrent@web.ghtorrent.org &

echo "Tunneling started"
