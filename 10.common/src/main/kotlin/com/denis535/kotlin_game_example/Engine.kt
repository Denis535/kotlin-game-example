package com.denis535.kotlin_game_example

import org.lwjgl.glfw.GLFW

public class Engine : AutoCloseable {

    private val MainWindow: MainWindow

    public var IsRunning: Boolean = false
        private set(value) {
            check(field != value)
            field = value
        }

    public var Tick: Int = 0
        get() {
            check(this.IsRunning)
            return field
        }
        private set(value) {
            check(this.IsRunning)
            field = value
        }

    public var Time: Double = 0.0
        get() {
            check(this.IsRunning)
            return field
        }
        private set(value) {
            check(this.IsRunning)
            field = value
        }

    private var PrevTime: Double? = null
        get() {
            check(this.IsRunning)
            return field
        }
        set(value) {
            check(this.IsRunning)
            field = value
        }

    public val DeltaTime: Double
        get() {
            check(this.IsRunning)
            return this.PrevTime?.let { prevTime -> this.Time - prevTime } ?: 0.0
        }

    public val FixedDeltaTime: Double
        get() {
            check(this.IsRunning)
            return 1.0 / 20.0
        }

    public constructor(mainWindow: MainWindow) {
        this.MainWindow = mainWindow.also { require(!it.IsClosed) }
    }

    public override fun close() {
        check(!this.MainWindow.IsClosed)
        check(!this.IsRunning)
    }

    public fun Run() {
        this.IsRunning = true
        this.Tick = 0
        this.Time = 0.0
        this.PrevTime = null
        while (!this.MainWindow.IsClosingRequested) {
            this.Time = this.MainWindow.Time
            this.OnFrame()
            this.Tick++
            this.PrevTime = this.Time
        }
        this.IsRunning = false
    }

    private fun OnFrame() {
        GLFW.glfwPollEvents().also { GLFW2.ThrowErrorIfNeeded() }

//        var accumulator = 0.0
//        accumulator += deltaTime
//        while (accumulator >= fixedDeltaTime) {
//            this.OnFixedUpdate()
//            accumulator -= fixedDeltaTime
//        }

        this.OnUpdate()
        this.OnDraw()
        GLFW.glfwSwapBuffers(this.MainWindow.NativeWindowPointer).also { GLFW2.ThrowErrorIfNeeded() }
    }

    private fun OnFixedUpdate() {

    }

    private fun OnUpdate() {
        if (GLFW.glfwGetKey(this.MainWindow.NativeWindowPointer, GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS || GLFW.glfwGetKey(this.MainWindow.NativeWindowPointer, GLFW.GLFW_KEY_RIGHT_ALT) == GLFW.GLFW_PRESS) {
            if (GLFW.glfwGetKey(this.MainWindow.NativeWindowPointer, GLFW.GLFW_KEY_ENTER) == GLFW.GLFW_PRESS) {
                this.MainWindow.IsFullscreen = !this.MainWindow.IsFullscreen
            }
        }
    }

    private fun OnDraw() {

    }

}
