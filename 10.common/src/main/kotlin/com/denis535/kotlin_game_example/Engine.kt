package com.denis535.kotlin_game_example

import org.lwjgl.glfw.GLFW

public object Engine {

    private var Window: Long = 0

    public fun Initialize() {
        check(this.Window == 0L)
        GLFW.glfwInit().also { GLFW2.ThrowErrorIfNeeded() }
    }

    public fun Deinitialize() {
        GLFW.glfwTerminate()
    }

    public fun ShowWindow(title: String, width: Int = 1280, height: Int = 720) {
        check(this.Window == 0L)
        this.Window = run {
            val monitor = GLFW.glfwGetPrimaryMonitor().also { GLFW2.ThrowErrorIfNeeded() }
            val videoMode = GLFW.glfwGetVideoMode(monitor)!!.also { GLFW2.ThrowErrorIfNeeded() }
            GLFW.glfwDefaultWindowHints().also { GLFW2.ThrowErrorIfNeeded() }
            GLFW.glfwWindowHint(GLFW.GLFW_POSITION_X, (videoMode.width() - width) / 2)
            GLFW.glfwWindowHint(GLFW.GLFW_POSITION_Y, (videoMode.height() - height) / 2)
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_CREATION_API, GLFW.GLFW_NATIVE_CONTEXT_API)
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4)
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 6)
            GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE)
            GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_TRUE)
            GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, 8)
            GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, 8)
            GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, 8)
            GLFW.glfwWindowHint(GLFW.GLFW_ALPHA_BITS, 8)
            GLFW.glfwWindowHint(GLFW.GLFW_DEPTH_BITS, 24)
            GLFW.glfwWindowHint(GLFW.GLFW_STENCIL_BITS, 8)
            GLFW.glfwCreateWindow(width, height, title, 0, 0).also { GLFW2.ThrowErrorIfNeeded() }
        }.also { window ->
            GLFW.glfwMakeContextCurrent(window).also { GLFW2.ThrowErrorIfNeeded() }
            GLFW.glfwSwapInterval(1).also { GLFW2.ThrowErrorIfNeeded() }
        }.also { window ->
            GLFW.glfwShowWindow(window).also { GLFW2.ThrowErrorIfNeeded() }
            GLFW.glfwFocusWindow(window).also { GLFW2.ThrowErrorIfNeeded() }
        }
    }

    public fun CloseWindow() {
        check(this.Window != 0L)
        GLFW.glfwSetWindowShouldClose(this.Window, true)
    }

    public fun Run() {
        check(this.Window != 0L)
        while (!GLFW.glfwWindowShouldClose(this.Window)) {
            GLFW.glfwPollEvents().also { GLFW2.ThrowErrorIfNeeded() }
            // Update()
            // Draw()
            GLFW.glfwSwapBuffers(this.Window).also { GLFW2.ThrowErrorIfNeeded() }
        }
        GLFW.glfwDestroyWindow(this.Window).also { GLFW2.ThrowErrorIfNeeded() }
        this.Window = 0L
    }

}
