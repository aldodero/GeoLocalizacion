import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import LoginPage from "./pages/Auth/LoginPage";

import WorkersPage from "./pages/Workers/WorkersPage";
import ProductsPage from "./pages/Products/ProductsPage";
import ReportsPage from "./pages/reports/ReportsPage";
import EventsPage from "./pages/events/EventsPage";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={<Navigate to="/login" replace />}
        />

        <Route
          path="/login"
          element={<LoginPage />}
        />

        <Route
          path="/workers"
          element={<WorkersPage />}
        />

        <Route
          path="/products"
          element={<ProductsPage />}
        />

        <Route
          path="/reports"
          element={<ReportsPage />}
        />

        <Route
          path="/events"
          element={<EventsPage />}
        />
      </Routes>
    </BrowserRouter>
  );
}