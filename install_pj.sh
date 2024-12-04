#!/bin/bash

INSTALL_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

if [ "$EUID" -ne 0 ]; then
    echo "Execute sudo with administrative privileges"
    echo "<sudo ./install_pj.sh"
    exit 1
fi

ln -sf "$INSTALL_DIR/pj" /usr/local/bin/pj

echo ""
echo "Type and enter 'pj' at the command-line to invoke the interpreter."