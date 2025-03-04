import React from 'react'
import Header from '../header/Header'
import { Outlet } from 'react-router-dom'

export default function SiteContainer() {
  return (
    <div>
        <Header />
        <Outlet />
    </div>
  )
}
