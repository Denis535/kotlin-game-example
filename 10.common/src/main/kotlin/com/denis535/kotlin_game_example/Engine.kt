package com.denis535.kotlin_game_example

import org.lwjgl.glfw.GLFW

public class Engine : AutoCloseable {

    private val Window: MainWindow
    private val OnFixedUpdateCallback: ((FrameInfo) -> Unit)
    private val OnUpdateCallback: ((FrameInfo) -> Unit)

    public var IsRunning: Boolean = false
        private set(value) {
            check(field != value)
            field = value
        }

    public var Fps: Double = 0.0
        get() {
            check(this.IsRunning)
            return field
        }
        private set(value) {
            check(this.IsRunning)
            field = value
        }

    public constructor(window: MainWindow, onFixedUpdateCallback: ((FrameInfo) -> Unit), onUpdateCallback: ((FrameInfo) -> Unit)) {
        this.Window = window.also { require(!it.IsClosed) }
        this.OnFixedUpdateCallback = onFixedUpdateCallback
        this.OnUpdateCallback = onUpdateCallback
    }

    public override fun close() {
        check(!this.Window.IsClosed)
        check(!this.IsRunning)
    }

    public fun Run(fixedDeltaTime: Double = 1.0 / 20.0) {
        this.IsRunning = true
        this.Fps = 0.0
        val info = FrameInfo()
        while (!this.Window.IsClosingRequested) {
            val deltaTime = run {
                val startTime = this.Window.Time
                this.OnFrameBegin()
                this.OnFixedUpdate(info)
                this.OnUpdate(info)
                this.OnFrameEnd()
                val endTime = this.Window.Time
                endTime - startTime
            }
            this.Fps = 1.0 / deltaTime
            info.FixedFrameInfo.DeltaTime = fixedDeltaTime
            info.Time += deltaTime
            info.DeltaTime = deltaTime
        }
        this.IsRunning = false
    }

    private fun OnFrameBegin() {
        GLFW.glfwPollEvents().also { GLFW2.ThrowErrorIfNeeded() }
    }

    private fun OnFixedUpdate(info: FrameInfo) {
        if (info.FixedFrameInfo.Number == 0) {
            this.OnFixedUpdateCallback(info)
            info.FixedFrameInfo.Number++
        } else {
            while (info.FixedFrameInfo.Time <= info.Time) {
                this.OnFixedUpdateCallback(info)
                info.FixedFrameInfo.Number++
            }
        }
    }

    private fun OnUpdate(info: FrameInfo) {
//        if (GLFW.glfwGetKey(this.Window.NativeWindowPointer, GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS || GLFW.glfwGetKey(this.Window.NativeWindowPointer, GLFW.GLFW_KEY_RIGHT_ALT) == GLFW.GLFW_PRESS) {
//            if (GLFW.glfwGetKey(this.Window.NativeWindowPointer, GLFW.GLFW_KEY_ENTER) == GLFW.GLFW_PRESS) {
//                this.Window.IsFullscreen = !this.Window.IsFullscreen
//            }
//        }
        this.OnUpdateCallback(info)
        info.Number++
    }

    private fun OnFrameEnd() {
        GLFW.glfwSwapBuffers(this.Window.NativeWindowPointer).also { GLFW2.ThrowErrorIfNeeded() }
    }

}

public class FrameInfo {

    public val FixedFrameInfo: FixedFrameInfo = FixedFrameInfo()

    public var Number: Int = 0
        internal set

    public var Time: Double = 0.0
        internal set

    public var DeltaTime: Double = 0.0
        internal set

    internal constructor()

    public override fun toString(): String {
        return "FrameInfo(Number=${this.Number}, Time=${this.Time})"
    }

}

public class FixedFrameInfo {

    public var Number: Int = 0
        internal set

    public val Time: Double
        get() {
            return this.Number * this.DeltaTime
        }

    public var DeltaTime: Double = 0.0
        internal set

    internal constructor()

    public override fun toString(): String {
        return "FixedFrameInfo(Number=${this.Number}, Time=${this.Time})"
    }

}
