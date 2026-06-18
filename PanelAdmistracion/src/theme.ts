import { createTheme } from "@mui/material/styles";

const theme = createTheme({
  palette: {
    primary: {
      main: "#FF9800",
    },
    background: {
      default: "#F5F5F5",
      paper: "#FFFFFF",
    },
  },

  shape: {
    borderRadius: 16,
  },

  typography: {
    fontFamily: "Inter, Arial, sans-serif",
  },
});

export default theme;