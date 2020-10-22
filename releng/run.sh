#!/usr/bin/env bash

set -eu

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Determine Python executable
if command -v python3 >/dev/null 2>&1; then
  PYTHON_CMD='python3'
elif command -v python >/dev/null 2>&1; then
  PYTHON_CMD='python'
else
  echo "Cannot find 'python3' or 'python' interpreter, please install Python 3"
  exit 1
fi

VENV="$DIR/.venv"

# Create virtual environment, if it does not exist yet
if [[ ! -d "$VENV" ]]
then
    # On Ubuntu/Debian, Python3 does not automatically come with ensurepip installed
    # https://bugs.launchpad.net/ubuntu/+source/python3.4/+bug/1290847/+index?comments=all
    # Note that the issue is marked as "fixed", but it's still broken.
    if ! $PYTHON_CMD -c 'help("modules")' 2> /dev/null | grep ensurepip > /dev/null
    then
        echo "Could not find the 'ensurepip' module, which is required for creating a"
        echo "Python virtual environment. There are a few options to fix this, for example:"
        echo "- Install python3-venv using your system's package manager, e.g.:"
        # This fix comes from comments #63-64 on the Ubuntu bug report
        echo "    sudo apt install python3-venv"
        echo "- Create the virtual environment manually without Pip and install Pip inside it:"
        # This fix comes from comment #58 on the Ubuntu bug report
        echo "    $PYTHON_CMD -m venv --without-pip \"$VENV\""
        echo "    curl -s https://bootstrap.pypa.io/get-pip.py | \"$VENV/bin/python\""
        echo "After executing one of these options, you can re-run this command."
        exit 1
    fi

    # This command is silent by itself, but errors (if any) are printed to stdout
    $PYTHON_CMD -m venv "$VENV"
fi

# Activate virtual environment
# (Since venv is made by idiots, temporarily disable unbound variable checks when activating virtual environment)
set +o nounset
source "$VENV/bin/activate"

# Install requirements
$PYTHON_CMD -m pip install --quiet --requirement "$DIR/requirements.txt"

# Run script
$PYTHON_CMD -u "$DIR/main.py" $*

# Deactivate virtualenv
deactivate
set -o nounset
