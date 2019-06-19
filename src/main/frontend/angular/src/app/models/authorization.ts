export interface Authorization {
  id: number;
  name: string;
  roles: string;
  access_token: string;
  refresh: string;
  token_type: string;
  expires_in: number;
}
