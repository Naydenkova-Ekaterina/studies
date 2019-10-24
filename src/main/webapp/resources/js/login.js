

var CONTEXT_PATH = $('#contextPathHolder').attr('data-contextPath');


class MyForm extends React.Component {
    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        data.forEach((value, key) => {data[key] = value});
        var json = JSON.stringify(data);


        console.log(json);
        var logObj = JSON.parse(json);
        var result = "email=" + logObj.email + "&password=" + logObj.password;
        console.log(result);

        fetch(CONTEXT_PATH + '/loginAction', {
            method: 'POST',
            headers: {
                'Content-Type': ' application/x-www-form-urlencoded'
            },
            body: result
        })

    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label htmlFor="email">Enter email</label>
                <input id="email" name="email" type="text" />

                <label htmlFor="password">Enter your password</label>
                <input id="password" name="password" type="password" />

                <button>SIGN IN</button>
            </form>
        );
    }
}

ReactDOM.render(<MyForm />, document.getElementById('app'));