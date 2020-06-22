Setup a virtualenv to run the api servers

(Only for unix based systems)
```bash
virtualenv -p python3 sentiment
source /sentiment/bin/activate
```
Windows users can skip this part and perform the following tasks as the unix-based system users.

After the virtualenv is setup, install the required packages using pip from `requirements.txt` using.

`pip3 install -r requirements.txt`

Finally, run `python sentiment_api.py` to run a server
