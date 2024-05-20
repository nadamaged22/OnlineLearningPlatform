import jwt from 'jsonwebtoken'


export const generateToken = ({ payload = {}, signature = "sarahagfuisg1654685", expiresIn = "1d" } = {}) => {
    const token = jwt.sign(payload, signature, { expiresIn: parseInt(expiresIn) });
    return token
}

export const verifyToken = ({ token, signature = "sarahagfuisg1654685"} = {}) => {
    const decoded = jwt.verify(token, signature);
    return decoded
}