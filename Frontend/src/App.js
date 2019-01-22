import React, {Component, Fragment} from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';

import Login from './views/Login/Login';
import Header from './views/Layout/Header/Header';
import PrivateRoute from './components/PrivateRoute/PrivateRoute'
import Sidebar from './views/Layout/Sidebar/Sidebar';
import Dashboard from './views/Dashboard/Dashboard';
import Profile from './views/Profile/Profile';
import Page404 from './views/Page404/Page404';

import { withStyles } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';

const styles =  theme => ({
    root: {
        display: 'flex',
    },
    toolbar: {
        marginTop: 24,
        ...theme.mixins.toolbar
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing.unit * 3,
    },
});


class App extends Component{

    state = {
        mobileDrawerOpen: false,
    };

    handleDrawerToggle = () => {
        this.setState(state => ({
                mobileDrawerOpen: !state.mobileDrawerOpen
            })
        );
    };

    render() {

        const { classes } = this.props;
        const { mobileDrawerOpen } = this.state;

        return (
            <BrowserRouter>

                <Fragment>

                    <CssBaseline/>

                    <Switch>

                        <Route path='/login' component={Login}/>

                        <Fragment>
                            <Header handleDrawerToggle={this.handleDrawerToggle}/>
                            <Sidebar isOpen={mobileDrawerOpen} handleDrawerToggle={this.handleDrawerToggle}/>

                            <main className={classes.content}>

                                <div className={classes.toolbar} />

                                <Switch>
                                    <PrivateRoute exact path='/' component={Dashboard}/>
                                    <PrivateRoute path='/profile' component={Profile}/>
                                    <PrivateRoute component={Page404}/>
                                </Switch>

                            </main>
                        </Fragment>

                    </Switch>

                </Fragment>

            </BrowserRouter>
        );
    }
}

export default withStyles(styles)(App);
