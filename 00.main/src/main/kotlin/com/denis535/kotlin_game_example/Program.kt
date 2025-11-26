package com.denis535.kotlin_game_example

import com.denis535.game_framework_pro.*

@JvmName("main")
fun Main(args: Array<String>) {
    Program().use {
    }
}

// Main
public class Program : AbstractProgram2<Theme, Screen, Router, Application> {

    public constructor() {
        this.Application = Application()
        this.Router = Router()
        this.Screen = Screen()
        this.Theme = Theme()
    }

    protected override fun OnClose() {
        this.Theme!!.close()
        this.Screen!!.close()
        this.Router!!.close()
        this.Application!!.close()
        super.OnClose()
    }

}
// UI
public class Theme : AbstractTheme2<Router, Application> {

    public constructor() {
        this.Machine.SetRoot(MainPlayList().State, null, null)
        this.Machine.SetRoot(GamePlayList().State, null, null)
    }

    protected override fun OnClose() {
        this.Machine.SetRoot(null, null, null)
        super.OnClose()
    }

}

public class MainPlayList : AbstractPlayList {

    public constructor()

    protected override fun OnClose() {
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}

public class GamePlayList : AbstractPlayList {

    public constructor()

    protected override fun OnClose() {
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}
// UI
public class Screen : AbstractScreen2<Router, Application> {

    public constructor() {
        this.Machine.SetRoot(RootWidget().Node, null, null)
    }

    protected override fun OnClose() {
        this.Machine.SetRoot(null, null, null)
        super.OnClose()
    }

}

public class RootWidget : AbstractWidget2 {

    public constructor() {
        this.NodeMutable.AddChild(MainWidget().Node, null)
        this.NodeMutable.AddChild(GameWidget().Node, null)
    }

    protected override fun OnClose() {
        this.NodeMutable.RemoveChildren({ true }, null, null)
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}

public class MainWidget : AbstractViewableWidget2 {
    internal class View {
        public constructor()
    }

    public constructor() {
        this.View = View()
    }

    protected override fun OnClose() {
        this.NodeMutable.RemoveChildren({ true }, null, null)
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}

public class GameWidget : AbstractViewableWidget2 {
    internal class View {
        public constructor()
    }

    public constructor() {
        this.View = View()
    }

    protected override fun OnClose() {
        this.NodeMutable.RemoveChildren({ true }, null, null)
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}
// UI
public class Router : AbstractRouter2<Theme, Screen, Application> {

    public constructor()

    protected override fun OnClose() {
        super.OnClose()
    }

}
// App
public class Application : AbstractApplication2 {

    public val Game: Game

    public constructor() {
        this.Game = Game()
    }

    protected override fun OnClose() {
        this.Game.close()
        super.OnClose()
    }

}
// Game
public class Game : AbstractGame2 {

    public constructor()

    protected override fun OnClose() {
        super.OnClose()
    }

}
